(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .controller('ObbligoInverdimentoDetailController', ObbligoInverdimentoDetailController);

    ObbligoInverdimentoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'ObbligoInverdimento', 'SuperficieInverdimento'];

    function ObbligoInverdimentoDetailController($scope, $rootScope, $stateParams, entity, ObbligoInverdimento, SuperficieInverdimento) {
        var vm = this;

        vm.obbligoInverdimento = entity;

        var unsubscribe = $rootScope.$on('istruttoriaApp:obbligoInverdimentoUpdate', function(event, result) {
            vm.obbligoInverdimento = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
