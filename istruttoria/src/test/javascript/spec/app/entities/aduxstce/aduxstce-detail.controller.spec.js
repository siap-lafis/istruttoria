'use strict';

describe('Controller Tests', function() {

    describe('Aduxstce Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockAduxstce;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockAduxstce = jasmine.createSpy('MockAduxstce');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Aduxstce': MockAduxstce
            };
            createController = function() {
                $injector.get('$controller')("AduxstceDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'istruttoriaApp:aduxstceUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
