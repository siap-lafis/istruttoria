(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .controller('SoggettoDetailController', SoggettoDetailController);

    SoggettoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Soggetto'];

    function SoggettoDetailController($scope, $rootScope, $stateParams, entity, Soggetto) {
        var vm = this;

        vm.soggetto = entity;

        var unsubscribe = $rootScope.$on('istruttoriaApp:soggettoUpdate', function(event, result) {
            vm.soggetto = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
