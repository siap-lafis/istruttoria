(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .controller('PenalitaController', PenalitaController);

//    PenalitaController.$inject = ['$scope', '$state', 'Penalita', 'PenalitaSearch'];
//
//    function PenalitaController ($scope, $state, Penalita, PenalitaSearch) {
//        var vm = this;
//        
//        vm.penalitas = [];
//        vm.search = search;
//        vm.loadAll = loadAll;
//
//        loadAll();
//
//        function loadAll() {
//            Penalita.query(function(result) {
//                vm.penalitas = result;
//            });
//        }
        
    // Paginazione
        PenalitaController.$inject = ['$scope', '$state', 'Penalita', 'PenalitaSearch','ParseLinks', 'AlertService', 'pagingParams', 'paginationConstants'];

        function PenalitaController ($scope, $state, Penalita, PenalitaSearch,ParseLinks, AlertService, pagingParams, paginationConstants) {
        	 var vm = this;
     	    
     	    vm.loadPage = loadPage;
     	    vm.search = search;
     	    vm.predicate = pagingParams.predicate;
     	    vm.reverse = pagingParams.ascending;
     	    vm.transition = transition;
     	    vm.itemsPerPage = paginationConstants.itemsPerPage;
     	    
     	    loadAll();
     	                              
     	    function loadAll () {
     	    	Penalita.query({
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
     	            vm.penalitas = data;
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
            PenalitaSearch.query({query: vm.searchQuery}, function(result) {
                vm.penalitas = result;
	            vm.totalItems = vm.capoPagatoes.length;
	            vm.queryCount = vm.capoPagatoes.length;
	            vm.page = 0;
            });
        }    
    }
})();
