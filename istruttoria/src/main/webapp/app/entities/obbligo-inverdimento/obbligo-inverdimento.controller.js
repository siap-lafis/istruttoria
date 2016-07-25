(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .controller('ObbligoInverdimentoController', ObbligoInverdimentoController);

//    ObbligoInverdimentoController.$inject = ['$scope', '$state', 'ObbligoInverdimento', 'ObbligoInverdimentoSearch'];
//
//    function ObbligoInverdimentoController ($scope, $state, ObbligoInverdimento, ObbligoInverdimentoSearch) {
//        var vm = this;
//        
//        vm.obbligoInverdimentos = [];
//        vm.search = search;
//        vm.loadAll = loadAll;
//
//        loadAll();
//
//        function loadAll() {
//            ObbligoInverdimento.query(function(result) {
//                vm.obbligoInverdimentos = result;
//            });
//        }
    
    // Paginazione
    ObbligoInverdimentoController.$inject = ['$scope', '$state', 'ObbligoInverdimento', 'ObbligoInverdimentoSearch', 'ParseLinks', 'AlertService', 'pagingParams', 'paginationConstants'];

    function ObbligoInverdimentoController ($scope, $state,ObbligoInverdimento,ObbligoInverdimentoSearch,ParseLinks, AlertService, pagingParams, paginationConstants) {    
	    var vm = this;
	    
	    vm.loadPage = loadPage;
	    vm.predicate = pagingParams.predicate;
	    vm.reverse = pagingParams.ascending;
	    vm.transition = transition;
	    vm.itemsPerPage = paginationConstants.itemsPerPage;
	    
	
	 
	   
	    loadAll();
	                              
	    function loadAll () {
	    	ObbligoInverdimento.query({
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
	            vm.obbligoInverdimentos = data;
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
            ObbligoInverdimentoSearch.query({query: vm.searchQuery}, function(result) {
                vm.obbligoInverdimentos = result;
            });
        }    }
})();
