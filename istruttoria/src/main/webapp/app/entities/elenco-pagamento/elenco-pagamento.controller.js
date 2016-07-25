(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .controller('ElencoPagamentoController', ElencoPagamentoController);

//    ElencoPagamentoController.$inject = ['$scope', '$state', 'ElencoPagamento', 'ElencoPagamentoSearch'];
//
//    function ElencoPagamentoController ($scope, $state, ElencoPagamento, ElencoPagamentoSearch) {
//        var vm = this;
//        
//        vm.elencoPagamentos = [];
//        vm.search = search;
//        vm.loadAll = loadAll;
//
//        loadAll();
//
//        function loadAll() {
//            ElencoPagamento.query(function(result) {
//            	vm.elencoPagamentos = result;
//            });
//        }

    // Paginazione
    ElencoPagamentoController.$inject = ['$scope', '$state', 'ElencoPagamento', 'ElencoPagamentoSearch','ParseLinks', 'AlertService', 'pagingParams', 'paginationConstants'];

    function ElencoPagamentoController ($scope, $state, ElencoPagamento, ElencoPagamentoSearch,ParseLinks, AlertService, pagingParams, paginationConstants) {    
	    var vm = this;
	    
	    vm.loadPage = loadPage;
	    vm.predicate = pagingParams.predicate;
	    vm.reverse = pagingParams.ascending;
	    vm.transition = transition;
	    vm.itemsPerPage = paginationConstants.itemsPerPage;
    
  
	    loadAll();
	                              
	    function loadAll () {
	    	ElencoPagamento.query({
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
	            vm.elencoPagamentos = data;
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
            ElencoPagamentoSearch.query({query: vm.searchQuery}, function(result) {
                vm.elencoPagamentos = result;
            });
        }    }
})();
