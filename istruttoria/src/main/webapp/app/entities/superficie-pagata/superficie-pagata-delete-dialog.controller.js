(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .controller('SuperficiePagataDeleteController',SuperficiePagataDeleteController);

    SuperficiePagataDeleteController.$inject = ['$uibModalInstance', 'entity', 'SuperficiePagata'];

    function SuperficiePagataDeleteController($uibModalInstance, entity, SuperficiePagata) {
        var vm = this;

        vm.superficiePagata = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SuperficiePagata.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
