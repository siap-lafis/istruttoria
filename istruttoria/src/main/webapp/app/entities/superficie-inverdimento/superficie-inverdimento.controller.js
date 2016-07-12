(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .controller('SuperficieInverdimentoController', SuperficieInverdimentoController);

    SuperficieInverdimentoController.$inject = ['$scope', '$state', 'SuperficieInverdimento', 'SuperficieInverdimentoSearch'];

    function SuperficieInverdimentoController ($scope, $state, SuperficieInverdimento, SuperficieInverdimentoSearch) {
        var vm = this;
        
        vm.superficieInverdimentos = [];
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            SuperficieInverdimento.query(function(result) {
                vm.superficieInverdimentos = result;
            });
        }

        function search () {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            SuperficieInverdimentoSearch.query({query: vm.searchQuery}, function(result) {
                vm.superficieInverdimentos = result;
            });
        }    }
})();
