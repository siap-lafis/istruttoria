(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .controller('SuperficiDomandaController', SuperficiDomandaController);

    SuperficiDomandaController.$inject = ['$scope', '$state', '$stateParams', 'SuperficiDomanda'];

    function SuperficiDomandaController ($scope, $state, $stateParams, SuperficiDomanda) {
        var vm = this;
        
        vm.superficies = [];
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
        	SuperficiDomanda.query({id : $stateParams.id}, function(result) {
                vm.superficies = result;
            });
        }

    }
})();
