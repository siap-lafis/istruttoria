(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .controller('DomandaDialogController', DomandaDialogController);

    DomandaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Domanda', 'Soggetto'];

    function DomandaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Domanda, Soggetto) {
        var vm = this;

        vm.domanda = entity;
        vm.clear = clear;
        vm.save = save;
        vm.soggettos = Soggetto.query({filter: 'domanda-is-null'});
        $q.all([vm.domanda.$promise, vm.soggettos.$promise]).then(function() {
            if (!vm.domanda.soggetto || !vm.domanda.soggetto.id) {
                return $q.reject();
            }
            return Soggetto.get({id : vm.domanda.soggetto.id}).$promise;
        }).then(function(soggetto) {
            vm.soggettos.push(soggetto);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.domanda.id !== null) {
                Domanda.update(vm.domanda, onSaveSuccess, onSaveError);
            } else {
                Domanda.save(vm.domanda, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('istruttoriaApp:domandaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
