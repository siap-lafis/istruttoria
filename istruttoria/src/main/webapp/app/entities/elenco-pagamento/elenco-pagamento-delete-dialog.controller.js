(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .controller('ElencoPagamentoDeleteController',ElencoPagamentoDeleteController);

    ElencoPagamentoDeleteController.$inject = ['$uibModalInstance', 'entity', 'ElencoPagamento'];

    function ElencoPagamentoDeleteController($uibModalInstance, entity, ElencoPagamento) {
        var vm = this;

        vm.elencoPagamento = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ElencoPagamento.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
