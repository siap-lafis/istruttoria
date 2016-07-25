(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .controller('SoggettoController', SoggettoController);

//    SoggettoController.$inject = ['$scope', '$state', '$window', 'Soggetto', 'SoggettoSearch', 'SoggettoReport'];
//
//    function SoggettoController ($scope, $state, $window, Soggetto, SoggettoSearch, SoggettoReport) {
//        var vm = this;
//        
//        vm.soggettos = [];
//        vm.search = search;
//        vm.report = report;
//        vm.loadAll = loadAll;
//
//        loadAll();
//
//        function loadAll() {
//            Soggetto.query(function(result) {
//                vm.soggettos = result;
//            });
//        }
    // Paginazione
    SoggettoController.$inject = ['$scope', '$state', '$window', 'Soggetto', 'SoggettoSearch', 'SoggettoReport','ParseLinks', 'AlertService', 'pagingParams', 'paginationConstants'];

    function SoggettoController ($scope, $state,$window,Soggetto,SoggettoSearch,SoggettoReport,ParseLinks, AlertService, pagingParams, paginationConstants) {    
	    var vm = this;
	    
	    vm.loadPage = loadPage;
	    vm.predicate = pagingParams.predicate;
	    vm.reverse = pagingParams.ascending;
	    vm.transition = transition;
	    vm.itemsPerPage = paginationConstants.itemsPerPage;
	    
	
	 
	   
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
            });
        }    
        
        function report () {
        	SoggettoReport.generate({}, function(result) {
        	    // la redirect non viene eseguita
        	    $window.location.href = '/content/reports/report.pdf';
        	});
        }    
        
    }
})();
