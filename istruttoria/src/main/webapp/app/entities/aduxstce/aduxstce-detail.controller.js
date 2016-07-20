(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .controller('AduxstceDetailController', AduxstceDetailController);

    AduxstceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Aduxstce'];

    function AduxstceDetailController($scope, $rootScope, $stateParams, entity, Aduxstce) {
        var vm = this;

        vm.aduxstce = entity;

        var unsubscribe = $rootScope.$on('istruttoriaApp:aduxstceUpdate', function(event, result) {
            vm.aduxstce = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
