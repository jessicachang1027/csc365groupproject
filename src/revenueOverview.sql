/* (SELECT RoomName, JanRev, FebRev, MarRev, AprRev, MayRev, JunRev, 
		JulRev, AugRev, SepRev, OctRev, NovRev, DecRev,
        RoomName + JanRev + FebRev + MarRev + AprRev + MayRev + JunRev + 
		JulRev + AugRev + SepRev + OctRev + NovRev + DecRev as TotalRev
 FROM
	(SELECT RoomName, getMonthRev(1, RoomId, inYear) as JanRev, getMonthRev(2, RoomId, inYear) as FebRev,
			getMonthRev(3, RoomId, inYear) as MarRev, getMonthRev(4, RoomId, inYear) as AprRev,
			getMonthRev(5, RoomId, inYear) as MayRev, getMonthRev(6, RoomId, inYear) as JunRev,
			getMonthRev(7, RoomId, inYear) as JulRev, getMonthRev(8, RoomId, inYear) as AugRev,
			getMonthRev(9, RoomId, inYear) as SepRev, getMonthRev(10, RoomId, inYear) as OctRev,
			getMonthRev(11, RoomId, inYear) as NovRev, getMonthRev(12, RoomId, inYear) as DecRev
	FROM Rooms
    UNION SELECT 'Total:', getTotalRev(1, inYear) as JanRev, getTotalRev(2, inYear) as FebRev,
			getTotalRev(3, inYear) as MarRev, getTotalRev(4, inYear) as AprRev,
			getTotalRev(5, inYear) as MayRev, getTotalRev(6, inYear) as JunRev,
			getTotalRev(7, inYear) as JulRev, getTotalRev(8, inYear) as AugRev,
			getTotalRev(9, inYear) as SepRev, getTotalRev(10, inYear) as OctRev,
			getTotalRev(11, inYear) as NovRev, getTotalRev(12, inYear) as DecRev) as revs); */