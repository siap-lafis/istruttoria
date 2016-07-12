'use strict';

describe('Controller Tests', function() {

    describe('Domanda Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockDomanda, MockSoggetto;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockDomanda = jasmine.createSpy('MockDomanda');
            MockSoggetto = jasmine.createSpy('MockSoggetto');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Domanda': MockDomanda,
                'Soggetto': MockSoggetto
            };
            createController = function() {
                $injector.get('$controller')("DomandaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'istruttoriaApp:domandaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
