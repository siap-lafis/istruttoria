(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .controller('DomandaDetailController', DomandaDetailController);

    DomandaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Domanda', 'Soggetto'];

    function DomandaDetailController($scope, $rootScope, $stateParams, entity, Domanda, Soggetto) {
        var vm = this;

        vm.domanda = entity;

        var unsubscribe = $rootScope.$on('istruttoriaApp:domandaUpdate', function(event, result) {
            vm.domanda = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
