(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .controller('DomandaController', DomandaController);

//    DomandaController.$inject = ['$scope', '$state', 'Domanda', 'DomandaSearch'];
//
//    function DomandaController ($scope, $state, Domanda, DomandaSearch) {
//        var vm = this;
//        
//        vm.domandas = [];
//        vm.search = search;
//        vm.loadAll = loadAll;
//
//        loadAll();
//
//        function loadAll() {
//            Domanda.query(function(result) {
//                vm.domandas = result;
//            });
//        }
    
    
 // Paginazione
    DomandaController.$inject = ['$scope', '$state', 'Domanda', 'DomandaSearch','ParseLinks', 'AlertService', 'pagingParams', 'paginationConstants'];

    function DomandaController ($scope, $state, Domanda, DomandaSearch,ParseLinks, AlertService, pagingParams, paginationConstants) {    
	    var vm = this;
	    
	    vm.loadPage = loadPage;
	    vm.search = search;
	    vm.predicate = pagingParams.predicate;
	    vm.reverse = pagingParams.ascending;
	    vm.transition = transition;
	    vm.itemsPerPage = paginationConstants.itemsPerPage;
	    
	    loadAll();
	                              
	    function loadAll () {
	    	Domanda.query({
	            page: pagingParams.page - 1,
	            size: vm.itemsPerPage,
	            sort: sort()
	        }, onSuccess, onError);
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
	            vm.domandas = data;
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
	
	    function search () {
	    	
	        if (!vm.searchQuery) {
	            return vm.loadAll();
	        }
	        DomandaSearch.query({query: vm.searchQuery}, function(result) {
	            vm.domandas = result;
	            vm.totalItems = vm.domandas.length;
	            vm.queryCount = vm.domandas.length;
	            vm.page = 0;
	        });
	    }    
    }
})();
