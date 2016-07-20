(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .controller('AduxstceDialogController', AduxstceDialogController);

    AduxstceDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Aduxstce'];

    function AduxstceDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Aduxstce) {
        var vm = this;

        vm.aduxstce = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.aduxstce.id !== null) {
                Aduxstce.update(vm.aduxstce, onSaveSuccess, onSaveError);
            } else {
                Aduxstce.save(vm.aduxstce, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('istruttoriaApp:aduxstceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
