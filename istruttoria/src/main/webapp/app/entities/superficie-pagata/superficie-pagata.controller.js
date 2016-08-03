(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .controller('SuperficiePagataController', SuperficiePagataController);

//    SuperficiePagataController.$inject = ['$scope', '$state', 'SuperficiePagata', 'SuperficiePagataSearch'];
//
//    function SuperficiePagataController ($scope, $state, SuperficiePagata, SuperficiePagataSearch) {
//        var vm = this;
//        
//        vm.superficiePagatas = [];
//        vm.search = search;
//        vm.loadAll = loadAll;
//
//        loadAll();
//
//        function loadAll() {
//            SuperficiePagata.query(function(result) {
//                vm.superficiePagatas = result;
//            });
//        }
        
        SuperficiePagataController.$inject = ['$scope', '$state', 'SuperficiePagata', 'SuperficiePagataSearch','ParseLinks', 'AlertService', 'pagingParams', 'paginationConstants'];

        function SuperficiePagataController ($scope, $state, SuperficiePagata, SuperficiePagataSearch,ParseLinks, AlertService, pagingParams, paginationConstants) {
        	var vm = this;
            
            vm.loadPage = loadPage;
            vm.predicate = pagingParams.predicate;
            vm.reverse = pagingParams.ascending;
            vm.transition = transition;
            vm.itemsPerPage = paginationConstants.itemsPerPage;
            

         
           
            loadAll();
                                      
            function loadAll () {
            	SuperficiePagata.query({
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
                    vm.superficiePagatas = data;
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
            SuperficiePagataSearch.query({query: vm.searchQuery}, function(result) {
                vm.superficiePagatas = result;
            });
        }    }
})();
