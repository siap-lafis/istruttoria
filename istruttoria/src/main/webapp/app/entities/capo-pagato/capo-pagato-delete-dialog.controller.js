(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .controller('CapoPagatoDeleteController',CapoPagatoDeleteController);

    CapoPagatoDeleteController.$inject = ['$uibModalInstance', 'entity', 'CapoPagato'];

    function CapoPagatoDeleteController($uibModalInstance, entity, CapoPagato) {
        var vm = this;

        vm.capoPagato = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CapoPagato.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
