'use strict';

describe('Controller Tests', function() {

    describe('Penalita Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPenalita, MockPagamento;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPenalita = jasmine.createSpy('MockPenalita');
            MockPagamento = jasmine.createSpy('MockPagamento');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Penalita': MockPenalita,
                'Pagamento': MockPagamento
            };
            createController = function() {
                $injector.get('$controller')("PenalitaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'istruttoriaApp:penalitaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
