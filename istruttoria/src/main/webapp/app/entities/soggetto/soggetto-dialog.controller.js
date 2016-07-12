(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .controller('SoggettoDialogController', SoggettoDialogController);

    SoggettoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Soggetto'];

    function SoggettoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Soggetto) {
        var vm = this;

        vm.soggetto = entity;
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
            if (vm.soggetto.id !== null) {
                Soggetto.update(vm.soggetto, onSaveSuccess, onSaveError);
            } else {
                Soggetto.save(vm.soggetto, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('istruttoriaApp:soggettoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
