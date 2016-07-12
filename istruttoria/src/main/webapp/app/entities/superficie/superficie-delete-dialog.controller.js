(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .controller('SuperficieDeleteController',SuperficieDeleteController);

    SuperficieDeleteController.$inject = ['$uibModalInstance', 'entity', 'Superficie'];

    function SuperficieDeleteController($uibModalInstance, entity, Superficie) {
        var vm = this;

        vm.superficie = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Superficie.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
