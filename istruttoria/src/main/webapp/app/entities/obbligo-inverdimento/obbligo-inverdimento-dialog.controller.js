(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .controller('ObbligoInverdimentoDialogController', ObbligoInverdimentoDialogController);

    ObbligoInverdimentoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'ObbligoInverdimento', 'SuperficieInverdimento'];

    function ObbligoInverdimentoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, ObbligoInverdimento, SuperficieInverdimento) {
        var vm = this;

        vm.obbligoInverdimento = entity;
        vm.clear = clear;
        vm.save = save;
        vm.superficiinverdimentos = SuperficieInverdimento.query({filter: 'obbligoinverdimento-is-null'});
        $q.all([vm.obbligoInverdimento.$promise, vm.superficiinverdimentos.$promise]).then(function() {
            if (!vm.obbligoInverdimento.superficiInverdimento || !vm.obbligoInverdimento.superficiInverdimento.id) {
                return $q.reject();
            }
            return SuperficieInverdimento.get({id : vm.obbligoInverdimento.superficiInverdimento.id}).$promise;
        }).then(function(superficiInverdimento) {
            vm.superficiinverdimentos.push(superficiInverdimento);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.obbligoInverdimento.id !== null) {
                ObbligoInverdimento.update(vm.obbligoInverdimento, onSaveSuccess, onSaveError);
            } else {
                ObbligoInverdimento.save(vm.obbligoInverdimento, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('istruttoriaApp:obbligoInverdimentoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
