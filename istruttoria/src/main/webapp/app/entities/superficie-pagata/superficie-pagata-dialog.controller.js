(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .controller('SuperficiePagataDialogController', SuperficiePagataDialogController);

    SuperficiePagataDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'SuperficiePagata', 'Pagamento'];

    function SuperficiePagataDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, SuperficiePagata, Pagamento) {
        var vm = this;

        vm.superficiePagata = entity;
        vm.clear = clear;
        vm.save = save;
        vm.pagamentos = Pagamento.query({filter: 'superficiepagata-is-null'});
        $q.all([vm.superficiePagata.$promise, vm.pagamentos.$promise]).then(function() {
            if (!vm.superficiePagata.pagamento || !vm.superficiePagata.pagamento.id) {
                return $q.reject();
            }
            return Pagamento.get({id : vm.superficiePagata.pagamento.id}).$promise;
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
            if (vm.superficiePagata.id !== null) {
                SuperficiePagata.update(vm.superficiePagata, onSaveSuccess, onSaveError);
            } else {
                SuperficiePagata.save(vm.superficiePagata, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('istruttoriaApp:superficiePagataUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
