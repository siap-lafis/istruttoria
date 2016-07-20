(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .controller('AduxstceController', AduxstceController);

    AduxstceController.$inject = ['$scope', '$state', 'Aduxstce', 'AduxstceSearch'];

    function AduxstceController ($scope, $state, Aduxstce, AduxstceSearch) {
        var vm = this;
        
        vm.aduxstces = [];
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Aduxstce.query(function(result) {
                vm.aduxstces = result;
            });
        }

        function search () {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            AduxstceSearch.query({query: vm.searchQuery}, function(result) {
                vm.aduxstces = result;
            });
        }    }
})();
