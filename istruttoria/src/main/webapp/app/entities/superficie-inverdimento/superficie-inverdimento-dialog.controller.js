(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .controller('SuperficieInverdimentoDialogController', SuperficieInverdimentoDialogController);

    SuperficieInverdimentoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'SuperficieInverdimento', 'Domanda'];

    function SuperficieInverdimentoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, SuperficieInverdimento, Domanda) {
        var vm = this;

        vm.superficieInverdimento = entity;
        vm.clear = clear;
        vm.save = save;
        vm.domandas = Domanda.query({filter: 'superficieinverdimento-is-null'});
        $q.all([vm.superficieInverdimento.$promise, vm.domandas.$promise]).then(function() {
            if (!vm.superficieInverdimento.domanda || !vm.superficieInverdimento.domanda.id) {
                return $q.reject();
            }
            return Domanda.get({id : vm.superficieInverdimento.domanda.id}).$promise;
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
            if (vm.superficieInverdimento.id !== null) {
                SuperficieInverdimento.update(vm.superficieInverdimento, onSaveSuccess, onSaveError);
            } else {
                SuperficieInverdimento.save(vm.superficieInverdimento, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('istruttoriaApp:superficieInverdimentoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
