(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .controller('SuperficieInverdimentoDetailController', SuperficieInverdimentoDetailController);

    SuperficieInverdimentoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'SuperficieInverdimento', 'Domanda'];

    function SuperficieInverdimentoDetailController($scope, $rootScope, $stateParams, entity, SuperficieInverdimento, Domanda) {
        var vm = this;

        vm.superficieInverdimento = entity;

        var unsubscribe = $rootScope.$on('istruttoriaApp:superficieInverdimentoUpdate', function(event, result) {
            vm.superficieInverdimento = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
