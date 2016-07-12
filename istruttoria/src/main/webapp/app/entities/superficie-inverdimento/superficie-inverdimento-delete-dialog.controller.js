(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .controller('SuperficieInverdimentoDeleteController',SuperficieInverdimentoDeleteController);

    SuperficieInverdimentoDeleteController.$inject = ['$uibModalInstance', 'entity', 'SuperficieInverdimento'];

    function SuperficieInverdimentoDeleteController($uibModalInstance, entity, SuperficieInverdimento) {
        var vm = this;

        vm.superficieInverdimento = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SuperficieInverdimento.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
