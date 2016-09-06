(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .controller('SuperficieController', SuperficieController);

//     SuperficieController.$inject = ['$scope', '$state', 'Superficie', 'SuperficieSearch'];

//    function SuperficieController ($scope, $state, Superficie, SuperficieSearch) {
//        var vm = this;
//        
//        vm.superficies = [];
//        vm.search = search;
//        vm.loadAll = loadAll;
//
//        loadAll();
//
//        function loadAll() {
//            Superficie.query(function(result) {
//                vm.superficies = result;
//            });
//        }
    
    // Paginazione
    SuperficieController.$inject = ['$scope', '$state', 'Superficie', 'SuperficieSearch','ParseLinks', 'AlertService', 'pagingParams', 'paginationConstants'];

    function SuperficieController ($scope, $state, Superficie, SuperficieSearch,ParseLinks, AlertService, pagingParams, paginationConstants) {    
    var vm = this;
    
    vm.loadPage = loadPage;
    vm.predicate = pagingParams.predicate;
    vm.reverse = pagingParams.ascending;
    vm.transition = transition;
    vm.search = search;
    vm.itemsPerPage = paginationConstants.itemsPerPage;
    
    vm.clear = clear;    
	vm.loadAll = loadAll;
    vm.searchQuery = pagingParams.search;
    vm.currentSearch = pagingParams.search;

 
   
    loadAll();
                              
    function loadAll () {
    	if (pagingParams.search) {	
    		SuperficieSearch.query({
    			query: pagingParams.search,
    			page: pagingParams.page - 1,
    			size: vm.itemsPerPage,
    			sort: sort()
    		}, onSuccess, onError);
    	}else {
    		Superficie.query({
    			page: pagingParams.page - 1,
    			size: vm.itemsPerPage,
    			sort: sort()
        }, onSuccess, onError);
    }
        function sort() {
        
        	var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
            if (vm.predicate !== 'id') {
                result.push('id');
            }
            return result;
        }
        function onSuccess(data, headers) {
            vm.links = ParseLinks.parse(headers('link'));
            vm.totalItems = headers('X-Total-Count');
            vm.queryCount = vm.totalItems;
            vm.superficies = data;
            vm.page = pagingParams.page;
        }
        function onError(error) {
            AlertService.error(error.data.message);
        }
    }
    
           

    function loadPage (page) {
        vm.page = page;
        vm.transition();
    }

        
    function transition () {
        $state.transitionTo($state.$current, {
            page: vm.page,
            sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
            search: vm.currentSearch
        });
    }

    function search (searchQuery) {
        if (!searchQuery){
            return vm.clear();
        }
        vm.links = null;
        vm.page = 1;
        vm.predicate = 'id';
        vm.reverse = false;
        vm.currentSearch = searchQuery;
        vm.transition();
    }

    function clear () {
        vm.links = null;
        vm.page = 1;
        vm.predicate = 'id';
        vm.reverse = true;
        vm.currentSearch = null;
        vm.transition();
    }
   }
})();
