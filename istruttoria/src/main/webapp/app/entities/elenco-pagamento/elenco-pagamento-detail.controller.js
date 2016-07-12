(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .controller('ElencoPagamentoDetailController', ElencoPagamentoDetailController);

    ElencoPagamentoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'ElencoPagamento', 'Domanda'];

    function ElencoPagamentoDetailController($scope, $rootScope, $stateParams, entity, ElencoPagamento, Domanda) {
        var vm = this;

        vm.elencoPagamento = entity;

        var unsubscribe = $rootScope.$on('istruttoriaApp:elencoPagamentoUpdate', function(event, result) {
            vm.elencoPagamento = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
