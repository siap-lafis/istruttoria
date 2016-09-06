(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .controller('SuperficieInverdimentoController', SuperficieInverdimentoController);

//    SuperficieInverdimentoController.$inject = ['$scope', '$state', 'SuperficieInverdimento', 'SuperficieInverdimentoSearch'];
//
//    function SuperficieInverdimentoController ($scope, $state, SuperficieInverdimento, SuperficieInverdimentoSearch) {
//        var vm = this;
//        
//        vm.superficieInverdimentos = [];
//        vm.search = search;
//        vm.loadAll = loadAll;
//
//        loadAll();
//
//        function loadAll() {
//            SuperficieInverdimento.query(function(result) {
//                vm.superficieInverdimentos = result;
//            });
//        }
        
        // Paginazione
        SuperficieInverdimentoController.$inject = ['$scope', '$state', 'SuperficieInverdimento', 'SuperficieInverdimentoSearch','ParseLinks', 'AlertService', 'pagingParams', 'paginationConstants'];

        function SuperficieInverdimentoController ($scope, $state, SuperficieInverdimento, SuperficieInverdimentoSearch,ParseLinks, AlertService, pagingParams, paginationConstants) {
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
    	    		SuperficieInverdimentoSearch.query({
    	    			query: pagingParams.search,
    	    			page: pagingParams.page - 1,
    	    			size: vm.itemsPerPage,
    	    			sort: sort()
    	    		}, onSuccess, onError);
    	    	}else {
    	    		SuperficieInverdimento.query({
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
    	            vm.superficieInverdimentos = data;
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
