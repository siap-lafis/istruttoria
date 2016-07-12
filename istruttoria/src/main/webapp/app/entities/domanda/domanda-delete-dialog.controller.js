(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .controller('DomandaDeleteController',DomandaDeleteController);

    DomandaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Domanda'];

    function DomandaDeleteController($uibModalInstance, entity, Domanda) {
        var vm = this;

        vm.domanda = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Domanda.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
