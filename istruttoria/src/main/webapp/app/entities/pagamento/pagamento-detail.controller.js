(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .controller('PagamentoDetailController', PagamentoDetailController);

    PagamentoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Pagamento', 'ElencoPagamento'];

    function PagamentoDetailController($scope, $rootScope, $stateParams, entity, Pagamento, ElencoPagamento) {
        var vm = this;

        vm.pagamento = entity;

        var unsubscribe = $rootScope.$on('istruttoriaApp:pagamentoUpdate', function(event, result) {
            vm.pagamento = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
