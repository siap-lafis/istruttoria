(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .controller('ElencoPagamentoDialogController', ElencoPagamentoDialogController);

    ElencoPagamentoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'ElencoPagamento', 'Domanda'];

    function ElencoPagamentoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, ElencoPagamento, Domanda) {
        var vm = this;

        vm.elencoPagamento = entity;
        vm.clear = clear;
        vm.save = save;
        vm.domandas = Domanda.query({filter: 'elencopagamento-is-null'});
        $q.all([vm.elencoPagamento.$promise, vm.domandas.$promise]).then(function() {
            if (!vm.elencoPagamento.domanda || !vm.elencoPagamento.domanda.id) {
                return $q.reject();
            }
            return Domanda.get({id : vm.elencoPagamento.domanda.id}).$promise;
        }).then(function(domanda) {
            vm.domandas.push(domanda);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.elencoPagamento.id !== null) {
                ElencoPagamento.update(vm.elencoPagamento, onSaveSuccess, onSaveError);
            } else {
                ElencoPagamento.save(vm.elencoPagamento, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('istruttoriaApp:elencoPagamentoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
