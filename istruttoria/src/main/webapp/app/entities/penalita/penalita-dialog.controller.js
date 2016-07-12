(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .controller('PenalitaDialogController', PenalitaDialogController);

    PenalitaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Penalita', 'Pagamento'];

    function PenalitaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Penalita, Pagamento) {
        var vm = this;

        vm.penalita = entity;
        vm.clear = clear;
        vm.save = save;
        vm.pagamentos = Pagamento.query({filter: 'penalita-is-null'});
        $q.all([vm.penalita.$promise, vm.pagamentos.$promise]).then(function() {
            if (!vm.penalita.pagamento || !vm.penalita.pagamento.id) {
                return $q.reject();
            }
            return Pagamento.get({id : vm.penalita.pagamento.id}).$promise;
        }).then(function(pagamento) {
            vm.pagamentos.push(pagamento);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.penalita.id !== null) {
                Penalita.update(vm.penalita, onSaveSuccess, onSaveError);
            } else {
                Penalita.save(vm.penalita, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('istruttoriaApp:penalitaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
