import { TestBed } from '@angular/core/testing';

import { UserbooklistService } from './userbooklist.service';

describe('UserbooklistService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: UserbooklistService = TestBed.get(UserbooklistService);
    expect(service).toBeTruthy();
  });
});
