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
        	DomandaDetailReport.generate(vm.domanda, function(result) {
        		// la redirect non viene eseguita
        		//$window.location.href = '/content/reports/reportChkLst.pdf';   
        		var fileName = result.name;       		       	       		      		     	       		
        		$window.open('/content/reports/'+fileName+'.pdf', '_blank', 'fullscreen=yes'); 
    	});
    }    
}
})();
