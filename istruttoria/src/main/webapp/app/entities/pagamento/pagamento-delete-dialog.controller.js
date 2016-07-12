(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .controller('PagamentoDeleteController',PagamentoDeleteController);

    PagamentoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Pagamento'];

    function PagamentoDeleteController($uibModalInstance, entity, Pagamento) {
        var vm = this;

        vm.pagamento = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Pagamento.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
