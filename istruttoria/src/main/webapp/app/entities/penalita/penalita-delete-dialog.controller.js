(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .controller('PenalitaDeleteController',PenalitaDeleteController);

    PenalitaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Penalita'];

    function PenalitaDeleteController($uibModalInstance, entity, Penalita) {
        var vm = this;

        vm.penalita = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Penalita.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
