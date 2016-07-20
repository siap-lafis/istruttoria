(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .controller('AduxstceDeleteController',AduxstceDeleteController);

    AduxstceDeleteController.$inject = ['$uibModalInstance', 'entity', 'Aduxstce'];

    function AduxstceDeleteController($uibModalInstance, entity, Aduxstce) {
        var vm = this;

        vm.aduxstce = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Aduxstce.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
