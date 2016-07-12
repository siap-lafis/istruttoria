(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .controller('SuperficieDialogController', SuperficieDialogController);

    SuperficieDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Superficie', 'Domanda'];

    function SuperficieDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Superficie, Domanda) {
        var vm = this;

        vm.superficie = entity;
        vm.clear = clear;
        vm.save = save;
        vm.domandas = Domanda.query({filter: 'superficie-is-null'});
        $q.all([vm.superficie.$promise, vm.domandas.$promise]).then(function() {
            if (!vm.superficie.domanda || !vm.superficie.domanda.id) {
                return $q.reject();
            }
            return Domanda.get({id : vm.superficie.domanda.id}).$promise;
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
            if (vm.superficie.id !== null) {
                Superficie.update(vm.superficie, onSaveSuccess, onSaveError);
            } else {
                Superficie.save(vm.superficie, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('istruttoriaApp:superficieUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
