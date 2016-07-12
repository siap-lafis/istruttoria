(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .controller('SuperficiePagataDetailController', SuperficiePagataDetailController);

    SuperficiePagataDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'SuperficiePagata', 'Pagamento'];

    function SuperficiePagataDetailController($scope, $rootScope, $stateParams, entity, SuperficiePagata, Pagamento) {
        var vm = this;

        vm.superficiePagata = entity;

        var unsubscribe = $rootScope.$on('istruttoriaApp:superficiePagataUpdate', function(event, result) {
            vm.superficiePagata = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
