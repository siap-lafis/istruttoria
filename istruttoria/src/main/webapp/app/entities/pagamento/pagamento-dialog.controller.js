(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .controller('PagamentoDialogController', PagamentoDialogController);

    PagamentoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Pagamento', 'ElencoPagamento'];

    function PagamentoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Pagamento, ElencoPagamento) {
        var vm = this;

        vm.pagamento = entity;
        vm.clear = clear;
        vm.save = save;
        vm.elencopagamentos = ElencoPagamento.query({filter: 'pagamento-is-null'});
        $q.all([vm.pagamento.$promise, vm.elencopagamentos.$promise]).then(function() {
            if (!vm.pagamento.elencoPagamento || !vm.pagamento.elencoPagamento.id) {
                return $q.reject();
            }
            return ElencoPagamento.get({id : vm.pagamento.elencoPagamento.id}).$promise;
        }).then(function(elencoPagamento) {
            vm.elencopagamentos.push(elencoPagamento);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.pagamento.id !== null) {
                Pagamento.update(vm.pagamento, onSaveSuccess, onSaveError);
            } else {
                Pagamento.save(vm.pagamento, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('istruttoriaApp:pagamentoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
