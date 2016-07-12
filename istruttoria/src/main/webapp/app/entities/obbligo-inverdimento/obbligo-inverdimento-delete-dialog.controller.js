(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .controller('ObbligoInverdimentoDeleteController',ObbligoInverdimentoDeleteController);

    ObbligoInverdimentoDeleteController.$inject = ['$uibModalInstance', 'entity', 'ObbligoInverdimento'];

    function ObbligoInverdimentoDeleteController($uibModalInstance, entity, ObbligoInverdimento) {
        var vm = this;

        vm.obbligoInverdimento = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ObbligoInverdimento.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
