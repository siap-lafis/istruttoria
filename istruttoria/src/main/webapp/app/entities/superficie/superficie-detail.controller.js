(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .controller('SuperficieDetailController', SuperficieDetailController);

    SuperficieDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Superficie', 'Domanda'];

    function SuperficieDetailController($scope, $rootScope, $stateParams, entity, Superficie, Domanda) {
        var vm = this;

        vm.superficie = entity;

        var unsubscribe = $rootScope.$on('istruttoriaApp:superficieUpdate', function(event, result) {
            vm.superficie = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
