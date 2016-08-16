(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .controller('SoggettoController', SoggettoController);

    // Paginazione
    SoggettoController.$inject = ['$scope', '$state', '$window', 'Soggetto', 'SoggettoSearch','ParseLinks', 'AlertService', 'pagingParams', 'paginationConstants'];

    function SoggettoController ($scope, $state, $window, Soggetto, SoggettoSearch, ParseLinks, AlertService, pagingParams, paginationConstants) {    
	    var vm = this;
	    
	    vm.loadPage = loadPage;
	    vm.search = search;
	    vm.predicate = pagingParams.predicate;
	    vm.reverse = pagingParams.ascending;
	    vm.transition = transition;
	    vm.itemsPerPage = paginationConstants.itemsPerPage;
	    vm.report = report;
	    
	    loadAll();
	                              
	    function loadAll () {
	    	Soggetto.query({
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
	            vm.soggettos = data;
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
            SoggettoSearch.query({query: vm.searchQuery}, function(result) {
                vm.soggettos = result;
	            vm.totalItems = vm.soggettos.length;
	            vm.queryCount = vm.soggettos.length;
	            vm.page = 0;
	        });
	    }    
        
        function report () {
            $window.open("api/report");
        }    
        
    }
})();
