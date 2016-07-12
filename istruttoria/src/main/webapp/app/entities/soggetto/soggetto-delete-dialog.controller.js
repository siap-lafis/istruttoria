(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .controller('SoggettoDeleteController',SoggettoDeleteController);

    SoggettoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Soggetto'];

    function SoggettoDeleteController($uibModalInstance, entity, Soggetto) {
        var vm = this;

        vm.soggetto = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Soggetto.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
