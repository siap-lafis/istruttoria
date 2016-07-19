(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .controller('DomandaDetailController', DomandaDetailController);

    DomandaDetailController.$inject = ['$scope','$window', '$rootScope', '$stateParams', 'entity', 'Domanda', 'Soggetto','DomandaDetailReport'];

    function DomandaDetailController($scope,$window, $rootScope, $stateParams, entity, Domanda, Soggetto,DomandaDetailReport) {
        var vm = this;

        vm.domanda = entity;
        vm.chkLst = chkLst;

        var unsubscribe = $rootScope.$on('istruttoriaApp:domandaUpdate', function(event, result) {
            vm.domanda = result;
        });
        $scope.$on('$destroy', unsubscribe);
    
        function chkLst() {
        	DomandaDetailReport.generate({}, function(result) {
        		// la redirect non viene eseguita
        		//$window.location.href = '/content/reports/reportChkLst.pdf';
        		$window.open('/content/reports/reportChkLst.pdf', '_blank', 'fullscreen=yes'); 
    	});
    }    
}
})();
