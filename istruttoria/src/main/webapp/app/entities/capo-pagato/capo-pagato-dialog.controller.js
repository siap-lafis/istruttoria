(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .controller('CapoPagatoDialogController', CapoPagatoDialogController);

    CapoPagatoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'CapoPagato', 'Pagamento'];

    function CapoPagatoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, CapoPagato, Pagamento) {
        var vm = this;

        vm.capoPagato = entity;
        vm.clear = clear;
        vm.save = save;
        vm.pagamentos = Pagamento.query({filter: 'capopagato-is-null'});
        $q.all([vm.capoPagato.$promise, vm.pagamentos.$promise]).then(function() {
            if (!vm.capoPagato.pagamento || !vm.capoPagato.pagamento.id) {
                return $q.reject();
            }
            return Pagamento.get({id : vm.capoPagato.pagamento.id}).$promise;
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
            if (vm.capoPagato.id !== null) {
                CapoPagato.update(vm.capoPagato, onSaveSuccess, onSaveError);
            } else {
                CapoPagato.save(vm.capoPagato, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('istruttoriaApp:capoPagatoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
