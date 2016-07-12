(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .controller('SuperficieController', SuperficieController);

    SuperficieController.$inject = ['$scope', '$state', 'Superficie', 'SuperficieSearch'];

    function SuperficieController ($scope, $state, Superficie, SuperficieSearch) {
        var vm = this;
        
        vm.superficies = [];
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Superficie.query(function(result) {
                vm.superficies = result;
            });
        }

        function search () {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            SuperficieSearch.query({query: vm.searchQuery}, function(result) {
                vm.superficies = result;
            });
        }    }
})();
