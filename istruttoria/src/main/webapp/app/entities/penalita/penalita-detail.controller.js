(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .controller('PenalitaDetailController', PenalitaDetailController);

    PenalitaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Penalita', 'Pagamento'];

    function PenalitaDetailController($scope, $rootScope, $stateParams, entity, Penalita, Pagamento) {
        var vm = this;

        vm.penalita = entity;

        var unsubscribe = $rootScope.$on('istruttoriaApp:penalitaUpdate', function(event, result) {
            vm.penalita = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
