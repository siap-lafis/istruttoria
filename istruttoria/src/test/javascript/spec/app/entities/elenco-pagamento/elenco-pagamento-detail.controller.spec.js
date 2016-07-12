'use strict';

describe('Controller Tests', function() {

    describe('ElencoPagamento Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockElencoPagamento, MockDomanda;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockElencoPagamento = jasmine.createSpy('MockElencoPagamento');
            MockDomanda = jasmine.createSpy('MockDomanda');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'ElencoPagamento': MockElencoPagamento,
                'Domanda': MockDomanda
            };
            createController = function() {
                $injector.get('$controller')("ElencoPagamentoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'istruttoriaApp:elencoPagamentoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
