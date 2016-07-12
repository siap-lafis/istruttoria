'use strict';

describe('Controller Tests', function() {

    describe('Soggetto Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockSoggetto;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockSoggetto = jasmine.createSpy('MockSoggetto');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Soggetto': MockSoggetto
            };
            createController = function() {
                $injector.get('$controller')("SoggettoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'istruttoriaApp:soggettoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
