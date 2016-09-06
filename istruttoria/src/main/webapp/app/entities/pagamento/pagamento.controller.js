(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .controller('PagamentoController', PagamentoController);

//    PagamentoController.$inject = ['$scope', '$state', 'Pagamento', 'PagamentoSearch'];
//
//    function PagamentoController ($scope, $state, Pagamento, PagamentoSearch) {
//        var vm = this;
//        
//        vm.pagamentos = [];
//        vm.search = search;
//        vm.loadAll = loadAll;
//
//        loadAll();
//
//        function loadAll() {
//            Pagamento.query(function(result) {
//                vm.pagamentos = result;
//            });
//        }
    
    // Paginazione
    PagamentoController.$inject = ['$scope', '$state', 'Pagamento', 'PagamentoSearch','ParseLinks', 'AlertService', 'pagingParams', 'paginationConstants'];

    function PagamentoController ($scope, $state, Pagamento, PagamentoSearch,ParseLinks, AlertService, pagingParams, paginationConstants) {
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
    	    		PagamentoSearch.query({
    	    			query: pagingParams.search,
    	    			page: pagingParams.page - 1,
    	    			size: vm.itemsPerPage,
    	    			sort: sort()
    	    		}, onSuccess, onError);
    	    	}else {
    	    		Pagamento.query({
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
    	            vm.pagamentos = data;
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
