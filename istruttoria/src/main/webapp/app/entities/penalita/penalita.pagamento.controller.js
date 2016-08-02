(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .controller('PenalitaPagamentoController', PenalitaPagamentoController);

    PenalitaPagamentoController.$inject = ['$scope', '$state', '$stateParams', 'PenalitaPagamento','ParseLinks', 'AlertService', 'pagingParams', 'paginationConstants'];

    function PenalitaPagamentoController ($scope, $state, $stateParams, PenalitaPagamento,ParseLinks, AlertService, pagingParams, paginationConstants) {
    	var vm = this;
      
      	vm.loadPage = loadPage;
      	vm.predicate = pagingParams.predicate;
      	vm.reverse = pagingParams.ascending;
      	vm.transition = transition;
      	vm.itemsPerPage = paginationConstants.itemsPerPage;
      	vm.id=$stateParams.id;
    	
    	loadAll();

        
        function loadAll () {
        	PenalitaPagamento.query({
        		   id : $stateParams.id,
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
    		$stateParams.id=vm.id;
    		$state.transitionTo($state.$current, {
    			id : vm.id,
    			page: vm.page,
    			sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
    			search: vm.currentSearch
    		});
    	}

    }      
})();
