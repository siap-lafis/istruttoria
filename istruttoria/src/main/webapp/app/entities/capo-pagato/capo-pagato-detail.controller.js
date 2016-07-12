(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .controller('CapoPagatoDetailController', CapoPagatoDetailController);

    CapoPagatoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'CapoPagato', 'Pagamento'];

    function CapoPagatoDetailController($scope, $rootScope, $stateParams, entity, CapoPagato, Pagamento) {
        var vm = this;

        vm.capoPagato = entity;

        var unsubscribe = $rootScope.$on('istruttoriaApp:capoPagatoUpdate', function(event, result) {
            vm.capoPagato = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
